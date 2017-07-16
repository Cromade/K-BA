const request = require('supertest');
const ModelIndex = require('../models');
const SessionTest = require('./session');

module.exports = function(app) {
    describe('POST /auth/subscribe', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .post('/auth/subscribe')
                .expect(401, done);
        });

        it('create user', function(done) {
            request(app)
            .post('/auth/subscribe')
            .send({
                email: "test@test.com",
                password: "test",
                gender: 'F'

            })
            .set('Content-Type', 'application/json')
            .expect(function(res) {
                res.body.uid = '0';
            })
            .expect(200, {
                email: "test@test.com",
                uid: '0',
                gender: 'F'
            }, done);
        });
    });


    describe('POST /auth/login', function() {

        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            })
        });

        it('response err 401', function(done) {
            request(app)
                .post('/auth/subscribe')
                .expect(401, done);
        });

        it('log user', function(done) {
            request(app)
            .post('/auth/login')
            .send({
                email: "test@test.com",
                password: "test"
            })
            .set('Content-Type', 'application/json')
            .expect(function(res) {
                res.body = {
                    email: res.body.email,
                    token: res.headers['token'] != undefined
                }
            })
            .expect(200, {
                email: "test@test.com",
                token: true
             }, done);
        });    

        it('test', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                console.log(token);
                done()
            })
        });
    });
}