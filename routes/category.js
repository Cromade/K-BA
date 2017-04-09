'use strict';

const express = require('express');
const UtilsIndex = require('../utils');
const ControllerIndex = require('../controllers');
const CategoryController = ControllerIndex.CategoryController;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

const router = express.Router();

router.post('/', (req, res, next) => {
    CategoryController.create(req.body.name).then((category) => {
        res.json(responsifier.instance(category));
    }).catch(next);
});


module.exports = router;