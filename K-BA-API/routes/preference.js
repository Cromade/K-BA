'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const PreferenceController = ControllerIndex.PreferenceController;
const ItemController = ControllerIndex.ItemController;
const GroupController = ControllerIndex.GroupController;

const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
    router.use(SessionMiddleware.getUser());

router.post('/', (req, res, next) => {
    return PreferenceController.create().then((preference) => {
        res.json(preference);
    }).catch(next); 
});

router.put('/:preference_uid/item/:item_uid', (req, res, next) => {
    PreferenceController.getByUid(req.params.preference_uid).then((preference) => {
        if(!preference) {
            return res.status(404).end();
        }
        return ItemController.getByUid(req.params.item_uid).then((item) => {
            if(!item) {
                return res.status(404).end();
            }
            return preference.addItem().then(() => {
                res.status(204).end();
            }).catch((error) => {
                console.log(error);
            })
        })
    }).catch(next);
});


router.put('/:preference_uid', (req, res, next) => {
    PreferenceController.modify(req.params.preference_uid, req.body).then((response) => {
        res.json(response);
    }).catch(next);
});


router.get('/', (req, res, next) => {
    PreferenceController.list('minimum').then((preference) => {
        res.json(preference);
    }).catch(next);
});

router.delete('/:preference_uid', (req, res, next)=> {
   PreferenceController.getByUid(req.params.preference_uid).then((preference) => {
       return preference.destroy().then((result) => {
            res.status(200).end();
       })
       
   }).catch(next);
});
module.exports = router;