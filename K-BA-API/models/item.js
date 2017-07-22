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
            allowNull: false,
            unique: true,
        },
        description: {
            type: DataTypes.STRING,
            allowNull: false
        },
        price: {
            type: DataTypes.FLOAT,
            allowNull: false
        },
        manufacturer: {
            type: DataTypes.STRING,
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
            },
            associateScopes: function(ModelIndex) {
                Item.addScope("minimum", {
                    attributes: ["uid", "name", "description", "price", "manufacturer"],
                    include: [{
                         model: ModelIndex.getModel('Category').scope("minimum"),
                        as: "categories"
                   }]
                })
            }
        },
        instanceMethods: {
            responsify: function () {
                const obj = {};
                obj.uid = this.uid;
                obj.name = this.name;
                obj.description = this.description;
                obj.price = this.price;
                return obj;
            }
        }
    });
    return Item;
};
