'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const PreferenceController = ControllerIndex.PreferenceController;
const ItemController = ControllerIndex.ItemController;
const GroupController = ControllerIndex.GroupController;
const ListController = ControllerIndex.ListController;

const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.get("/", (req, res, next) => {
    return ListController.listLists(req.user.id).then((lists) => {
        return Promise.all(lists.map((list) => {
            return list.getItems();
        })).then((allListItems) => {
            var itemCounter = {}
            allListItems.forEach((items) => {
                items.forEach((item) => {
                    if(itemCounter[item.uid] == undefined) {
                        itemCounter[item.uid] = {
                            item: item,
                            quantity: 0
                        }
                    }
                    itemCounter[item.uid].quantity++
                })
            })
            console.log(itemCounter);
            var resultItems = []
            var keys = Object.keys(itemCounter);
            keys.forEach((uid) => {
                var item = itemCounter[uid]
                if(item.quantity > 5) {
                    resultItems.push(item.item);
                }
            })
            res.json(resultItems)
        })
    }).catch(next);
});


module.exports = router;