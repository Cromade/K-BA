'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const ItemController = ControllerIndex.ItemController;
const CategoryController = ControllerIndex.CategoryController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.post('/', (req, res, next) => {
    ItemController.create(req.body.name, req.body.description, req.body.price, req.body.manufacturer).then((item) => {
        res.json(responsifier.instance(item));
    }).catch(next);
});

router.put('/:item_uid', (req, res, next) => {
        ItemController.modify(req.params.item_uid, req.body).then((response) => {
            res.json(response);
        }).catch(next);
});

router.put('/:item_uid/category/:category_uid', (req, res, next) => {
    ItemController.getByUid(req.params.item_uid).then((item) => {
        if(!item) {
            return res.status(404).end();
        }
        return CategoryController.getByUid(req.params.category_uid).then((category) => {
            if(!category) {
                return res.status(404).end();
            }
            return item.addCategory(category).then(() => {
                res.status(204).end();
            })
        })
    }).catch(next);
});

router.get('/', (req, res, next)=> {
   ItemController.listItems(req.query.search).then((items) => {
       res.json(items);
   }).catch(next);
});

router.get('/:item_uid', (req, res, next)=> {
   ItemController.getByUid(req.params.item_uid).then((item) => {
       res.json(item);
   }).catch(next);
});

router.delete('/:item_uid', (req, res, next)=> {
   ItemController.getByUid(req.query.item_uid).then((item) => {
       return item.destroy().then((result) => {
            res.json(result)
       })
       
   }).catch(next);
});
module.exports = router;