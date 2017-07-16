'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const CategoryController = ControllerIndex.CategoryController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.post('/',(req, res, next) => {
    CategoryController.create(req.body.name).then((category) => {
        res.json(responsifier.instance(category));
    }).catch(next);
});

router.get('/', (req, res, next)=> {
    CategoryController.listCategories().then((categories) => {
        res.json(categories);
    }).catch(next);
});
module.exports = router;