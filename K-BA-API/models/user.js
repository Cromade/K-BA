'use strict';

const UtilsIndex = require('../utils');
const CryptUtils = UtilsIndex.CryptUtils;
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the User model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const User = sequelize.define("User", {
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
        firstname: {
            type: DataTypes.STRING(150),
            allowNull: false,
        },
        lastname: {
            type: DataTypes.STRING(150),
            allowNull: false,
        },
        email: {
            type: DataTypes.STRING(150),
            allowNull: false,
            unique: true,
        },
        pseudo: {
            type: DataTypes.STRING(150),
            allowNull: false,
            unique: true,
        },
        password: {
            type: DataTypes.STRING(50),
            allowNull: false
        },
        birthdate: {
            type: DataTypes.DATE,
            allowNull: false,
        },
        path :{
            type: DataTypes.STRING,
        },
        salt: DataTypes.STRING(255)
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                User.belongsToMany(ModelIndex.getModel('Plugin'), {
                    as: 'plugins',
                    through: 'PluginUser'
                });
                User.belongsTo(ModelIndex.getModel('Address'), {
                    as: 'address',
                });
                User.belongsToMany(ModelIndex.getModel('Group'), {
                    as: 'groups',
                    through: 'GroupUser'
                });
                User.belongsToMany(ModelIndex.getModel('List'), {
                    as: 'lists',
                    through: 'UserList'
                });
            }
        },
        instanceMethods: {
            responsify: function () {
                const obj = {};
                obj.uid = this.uid;
                obj.pseudo = this.pseudo;
                return obj;
            },
            authenticate: function(clearPassword) {
                return CryptUtils.hashPassword(this.salt, clearPassword) === this.password;
            }
        }
    });
    return User;
};