const request = require('supertest');
const ModelIndex = require('../models');
const SessionTest = require('./session');
const assert = require('assert');

module.exports = function(app) {
    describe('POST /item', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .post('/item')
                .expect(401, done);
        });

        it('create item', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .post('/item')
                .send({
                    name: "banana",
                    price: 99
                })
                .set('Content-Type', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(function(res) {
                    res.body.uid = '0';
                })
                .expect(200, {
                    name: "banana",
                    uid: '0',
                    price: 99
                }, done);
            })
        });

    });



    describe('GET /item', function() {

        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

         it('response err 401', function(done) {
            request(app)
                .get('/item')
                .expect(401, done);
        });

        it('get item', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .get('/item')
                .set('Accept', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(200)
                .expect(res => {
                    assert(res.body.count > 0)
                })
                .end(done)
            });
        });

        it('get item with search (no result)', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .get('/item')
                .query({
                    search: "pi",
                })
                .set('Accept', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(200)
                .expect(res => {
                    assert(res.body.count == 0)
                })
                .end(done)

            });
        });

        it('get item with search (result) ', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .get('/item')
                .query({
                    search: "ba",
                })
                .set('Accept', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(200)
                .expect(res => {
                    assert(res.body.count != 0)
                })
                .end(done)

            });
        });
    });


    describe('PUT /item/:item_uid/category/:category_uid', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .put('/item/:item_uid/category/:category_uid')
                .expect(401, done);
        });

        it('add item to category', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .put('/item/4e4dcdf8-f2f3-4f81-9e32-81b22e21a141/category/177728ea-cf9e-40bf-a702-282e6bec5b98')
  
                .set('Content-Type', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(function(res) {
                    res.body.uid = '0';
                })
                .expect(204, {
                    uid: '0',
                }, done);
            })
        });
    });
}