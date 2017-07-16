const request = require('supertest');
const ModelIndex = require('../models');
const SessionTest = require('./session');

module.exports = function(app) {
    describe('POST /category', function() {
    
        before(() => {
            return ModelIndex.sequelize.sync({
                force: false
            });
        });

        it('response err 401', function(done) {
            request(app)
                .post('/category')
                .expect(401, done);
        });

       it('create category', function(done) {
            SessionTest.getToken('test@test.com').then((token) => {
                request(app)
                .post('/category')
                .send({
                    name: "hygiene",
                })
                .set('Content-Type', 'application/json')
                .set('Authorization', 'Bearer ' + token)
                .expect(function(res) {
                    res.body.uid = '0';
                })
                .expect(200, {
                    name: "hygiene",
                    uid: '0',

                }, done);
            })
        });
    });

}