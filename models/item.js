'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the Item model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const Item = sequelize.define("Item", {
        id: {
            type: DataTypes.BIGINT,
            primaryKey: true,
            autoIncrement: true
        },
        uid: {
            type: DataTypes.UUID,
            unique: true,
            defaultValue: DataTypes.UUIDV4,
            allowNull: false
        },
        name: {
            type: DataTypes.STRING(150),
            allowNull: false
        },
        price: {
            type: DataTypes.FLOAT,
            allowNull: false
        }
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                Item.belongsToMany(ModelIndex.getModel('Category'), {
                    as: 'categories',
                    through: 'CategoryItem'
                });
            }
        },
        instanceMethods: {
            responsify: function () {
            }
        }
    });
    return Item;
};
