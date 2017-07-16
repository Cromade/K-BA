const request = require('supertest');
const ModelIndex = require('../models');
const SessionTest = require('./session');

module.exports = function(app) {
    describe('POST /group', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .post('/group')
                .expect(401, done);
        });

        it('create group', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .post('/group')
                .send({
                    name: "home",
                    uid: '0'
                })
                .set('Content-Type', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(function(res) {
                    res.body.uid = '0';
                })
                .expect(200, {
                    name: "home",
                    uid: '0'
                }, done);
            })
        });
    });

    describe('PUT /group/:group_uid/user/:user_uid', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .put('/group/d19c038e-8203-43a7-89ca-8face48320d7/user/fbba7a4b-0b1f-4f54-94bc-4f4bfe0bfe6f')
                .expect(401, done);
        });

        it('add user to group', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .put('/group/d19c038e-8203-43a7-89ca-8face48320d7/user/fbba7a4b-0b1f-4f54-94bc-4f4bfe0bfe6f')
  
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