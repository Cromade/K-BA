'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the Group model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const Group = sequelize.define("Group", {
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
            type: DataTypes.STRING(50),
            allowNull: false
        }
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                Group.belongsToMany(ModelIndex.getModel('User'), {
                    as: 'users',
                    through: 'GroupUser'
                });
                Group.belongsToMany(ModelIndex.getModel('List'), {
                    as: 'lists',
                    through: 'GroupList'
                });

            }
        },
        instanceMethods: {
            responsify: function () {
            }
        }
    });
    return Group;
};
