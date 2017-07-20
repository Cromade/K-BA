'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const AddressController = ControllerIndex.AddressController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());


module.exports = router;
router.get('/:uid', (req, res, next)=> {
   AddressController.getByUid(req.params.uid, 'minimum').then((address) => {
       res.json(address);
   }).catch(next);
});