'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const ListController = ControllerIndex.ListController;
const ItemController = ControllerIndex.ItemController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.post('/', (req, res, next) => {
    ListController.create(req.body.name, req.body.state, req.user).then((list) => {
        res.json(responsifier.instance(list));
    }).catch(next);
});

router.put('/:list_uid/item/:item_uid', (req, res, next) => {
    ListController.getByUid(req.params.list_uid).then((list) => {
        if(!list) {
            return res.status(404).end();
        }
        return ItemController.getByUid(req.params.item_uid).then((item) => {
            if(!item) {
                return res.status(404).end();
            }
            return list.addItem(item, {
                quantity: req.body.quantity || 1
            }).then(() => {
                res.status(204).end();
            }).catch((error) => {
                console.log(error);
            })
        })
    }).catch(next);
});

router.get('/', (req, res, next)=> {
    ListController.listLists(req.user.id).then((lists) => {
        res.json(lists);
    }).catch(next);
});

module.exports = router;