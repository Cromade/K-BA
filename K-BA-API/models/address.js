'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the Address model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const Address = sequelize.define("Address", {
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
        address: {
            type: DataTypes.STRING(255),
            allowNull: false,
        },
        zipcode : DataTypes.BIGINT,
        town : DataTypes.STRING(50),
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                Address.belongsToMany(ModelIndex.getModel('User'), {
                    as: 'users',
                    through: 'AddressUser'
                });
            }
        },
        instanceMethods: {
            responsify: function () {
                const obj = {};
                obj.uid = this.uid;
                obj.address = this.address;
                obj.zipcode = this.zipcode;
                obj.town = this.town;
                return obj;
            }
        }
    });
    return Address;
};
