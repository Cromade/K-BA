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
        })).then((items) => {
            res.json(items);
        })
    }).catch(next);
});


module.exports = router;