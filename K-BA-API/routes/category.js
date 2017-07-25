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
        res.json(category);
    }).catch(next);
});

router.get('/', (req, res, next)=> {
    CategoryController.listCategories().then((categories) => {
        res.json(categories);
    }).catch(next);
});

router.put('/', (req, res, next)=> {
    CategoryController.modify().then((category) => {
        res.json(categories);
    }).catch(next);
});


router.delete('/:category_uid', (req, res, next)=> {
   CategoryController.getByUid(req.params.category_uid).then((category) => {
       return category.destroy().then((result) => {
            res.status(200).end();
       })
       
   }).catch(next);
});
module.exports = router;