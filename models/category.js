'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the Category model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const Category = sequelize.define("Category", {
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
        }
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                Category.belongsToMany(ModelIndex.getModel('Item'), {
                    as: 'items',
                    through: 'CategoryItem'
                });
            }
        },
        instanceMethods: {
            responsify: function () {
            }
        }
    });
    return Category;
};
