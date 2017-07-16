'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the Preference model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const Preference = sequelize.define("Preference", {
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
        }
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                Preference.belongsTo(ModelIndex.getModel('User'), {
                    as: 'user',
                });
                Preference.hasMany(ModelIndex.getModel('Item'), {
                    as: 'items',
                    through: 'PreferenceItem'
                });
            }
        },
        instanceMethods: {
            responsify: function () {
                const obj = {};
                obj.uid = this.uid;
                obj.name = this.name;
                if(this.items) {
                    obj.items = responsifier.list(this.items)
                }
                return obj;
            }
        }
    });
    return List;
};
