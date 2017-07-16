const request = require('supertest');
const ModelIndex = require('../models');
const SessionTest = require('./session');

module.exports = function(app) {
    describe('POST /list', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .post('/list')
                .expect(401, done);
        });

        it('create list', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .post('/list')
                .send({
                    uid: '0',
                    name: "groceries",
                    state: 'ONGOING'
                })
                .set('Content-Type', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(function(res) {
                    res.body.uid = '0';
                })
                .expect(200, {
                    uid: '0',
                    name: "groceries",
                    state: 'ONGOING'
                }, done);
            })
        });
    });

    describe('PUT /list/:list_uid/item/:item_uid', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .put('/list/:list_uid/item/:item_uid')
                .expect(401, done);
        });

        it('add item to list', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .put('/list/3f7720a6-c871-428e-bc62-71445256e1cb/item/4e4dcdf8-f2f3-4f81-9e32-81b22e21a141')
  
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