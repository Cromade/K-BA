'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.get('/', (req, res, next)=> {
   UserController.listUsers('minimum').then((users) => {
       res.json(users);
   }).catch(next);
});

router.get('/:uid', (req, res, next)=> {
   UserController.getByUid(req.params.uid, 'minimum').then((user) => {
       res.json(user);
   }).catch(next);
});

module.exports = router;