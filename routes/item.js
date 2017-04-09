'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const ItemController = ControllerIndex.ItemController;
const CategoryController = ControllerIndex.CategoryController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

const router = express.Router();

router.post('/', (req, res, next) => {
    ItemController.create(req.body.name, req.body.price).then((item) => {
        res.json(responsifier.instance(item));
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



module.exports = router;