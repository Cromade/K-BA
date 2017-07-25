'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const ListController = ControllerIndex.ListController;

const SessionController = ControllerIndex.SessionController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

const router = express.Router();

router.post('/subscribe', (req, res, next) => {
    console.log(req.body);
    if(req.body.firstname && req.body.lastname && req.body.email && req.body.password && req.body.pseudo && req.body.address && req.body.zipcode && req.body.city && req.body.birthdate ) {
        UserController.create(req.body.firstname, req.body.lastname, req.body.email, req.body.pseudo, req.body.password,  req.body.birthdate,req.body.address, req.body.zipcode, req.body.city).then((user) => {
            ListController.create
            res.json(responsifier.instance(user));
            }).catch(next);
    } else {
        res.status(401).end();
    }
});

router.post('/login', (req, res, next) => {
   UserController.getByEmail(req.body.email).then((user) => {
       if(user && user.authenticate(req.body.password)) {
            SessionController.create(user).then((session) => {
                res.set('Token', session.token);
                res.json(responsifier.instance(user));
           });
       } else {
           res.status(401);
           res.end();
       }
   }).catch(next);
});     

module.exports = router;