'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the ItemList model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const ItemList = sequelize.define("ItemList", {
        id: {
            type: DataTypes.BIGINT,
            primaryKey: true,
            autoIncrement: true
        },
        quantity: {
            type: DataTypes.BIGINT,
            allowNull: false
        }
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                const Item = ModelIndex.getModel('Item');
                const List = ModelIndex.getModel('List');
                List.belongsToMany(Item, {
                    as: 'items',
                    through: ItemList
                });
                Item.belongsToMany(List, {
                    as: 'lists',
                    through: ItemList
                });
            },
            associateScopes: function(ModelIndex) {
                ItemList.addScope("minimum", {
                    attributes: ["uid", "quantity"],
                })
            }
        },
        instanceMethods: {
            responsify: function () {
            }
        }
    });
    return ItemList;
};
