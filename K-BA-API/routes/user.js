'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const UserController = ControllerIndex.UserController;
const ListController = ControllerIndex.ListController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;
const MiddlewareIndex = require('../middlewares');
const AuthMiddleware = MiddlewareIndex.AuthMiddleware;
const SessionMiddleware = MiddlewareIndex.SessionMiddleware;

const router = express.Router();

router.use(AuthMiddleware.getToken());
router.use(SessionMiddleware.getUser());

router.get('/', (req, res, next) => {
    UserController.listUsers(req.query.search, 'minimum').then((users) => {
        res.json(users);
    }).catch(next);
});

router.get('/:uid', (req, res, next) => {
   UserController.getByUid(req.params.uid, 'minimum').then((user) => {
       res.json(user);
   }).catch(next);
});

router.put('/', (req, res, next) => {
    UserController.modify(req.user.uid, req.body).then((response) => {
        res.json(response);
    }).catch(next);
})

router.put('/addFavList/:list_uid', (req, res, next) => {
    ListController.getByUid(req.params.list_uid).then((list) => {
        req.user.addFavorite(list).then((response) => {
            res.json(response);
        });
    }).catch(next);; 
})

router.get('/getFavList/', (req, res, next) => {
    req.user.getFavorites().then((favorites) => {
        console.log(favorites);
        if(favorites != undefined && favorites.length > 0) {
            res.json(favorites[favorites.length - 1]);
        } else {
            res.status(404).end();
        }
    })
});

router.delete('/:user_uid', (req, res, next)=> {
   UserController.getByUid(req.params.user_uid).then((user) => {
       return user.destroy().then((result) => {
            res.status(200).end();
       })
       
   }).catch(next);
});
module.exports = router;