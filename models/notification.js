'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the Notification model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const Notification = sequelize.define("Notification", {
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
        date: {
            type: DataTypes.DATE,
            allowNull: false
        },
        text: {
            type: DataTypes.STRING(500),
            allowNull: false
        },
        state : DataTypes.STRING(10)
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                Notification.belongsTo(ModelIndex.getModel('User'), {
                    as: 'user',
                    foreignKey: {
                        allowNull: false
                    }
                });
            }
        },
        instanceMethods: {
            responsify: function () {
            }
        }
    });
    return Notification;
};
