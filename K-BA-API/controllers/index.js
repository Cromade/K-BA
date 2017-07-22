'use strict';

const UserController = require('./user');
const SessionController = require('./session');
const GroupController = require('./group');
const ListController = require('./list');
const ItemController = require('./item');
const PluginController = require('./plugin');
const AddressController = require('./address');
const CategoryController = require('./category');
const PreferenceController = require('./preference');


const ControllerIndex = {};
ControllerIndex.UserController = UserController;
ControllerIndex.SessionController = SessionController;
ControllerIndex.GroupController = GroupController;
ControllerIndex.ListController = ListController;
ControllerIndex.ItemController = ItemController;
ControllerIndex.PluginController = PluginController;
ControllerIndex.AddressController = AddressController;
ControllerIndex.CategoryController = CategoryController;
ControllerIndex.PreferenceController = PreferenceController;

module.exports = ControllerIndex;