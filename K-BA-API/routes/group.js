'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const GroupController = ControllerIndex.GroupController;
const UserController = ControllerIndex.UserController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.post('/', (req, res, next) => {
    GroupController.create(req.body.name, req.user.id).then((group) => {
        res.json(responsifier.instance(group));
    }).catch(next);
});

router.get('/', (req, res, next)=> {
    if(req.query.user_uid) {
        UserController.getByUid(req.query.user_uid).then((user) => {
        return GroupController.listGroups(user.id, req.query.search, "minimum").then((groups) => {
                res.json(groups);
            }).catch(next);
        })
    } else {
        GroupController.listGroups().then((groups) => {
            res.json(groups);
        }).catch(next);
    }
});

router.put('/:group_uid', (req, res, next) => {
        GroupController.modify(req.params.group_uid, req.body).then((response) => {
            res.json(response);
        }).catch(next);
});


router.put('/:group_uid/user/:user_uid', (req, res, next) => {
    GroupController.getByUid(req.params.group_uid).then((group) => {
        if(!group) {
            return res.status(404).end();
        }
        return UserController.getByUid(req.params.user_uid).then((user) => {
            if(!user) {
                return res.status(404).end();
            }
            return group.addUser(user).then(() => {
                res.status(204).end();
            })
        })
    }).catch(next);
});

router.delete('/:group_uid', (req, res, next)=> {
   GroupController.getByUid(req.params.group_uid).then((group) => {
       return group.destroy().then((result) => {
            res.status(200).end();
       })
       
   }).catch(next);
});

module.exports = router;