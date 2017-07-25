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
            type: DataTypes.STRING,
            allowNull: false,
        },
        salt: DataTypes.STRING(255)
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
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
                User.belongsToMany(ModelIndex.getModel('List'), {
                    as: 'favorites',
                    through: 'FavoriteList'
                });
            },
            associateScopes: function(ModelIndex) {
                User.addScope("minimum", {
                    attributes: ["uid", "firstname", "lastname", "pseudo", "email", "birthdate"],
                    include: [{
                         model: ModelIndex.Address.scope("minimum"),
                        as: "address"
                   }]
                })
            }
        },
        instanceMethods: {
            responsify: function () {
                const obj = {};
                obj.uid = this.uid;
                obj.lastname = this.lastname;
                obj.firstname = this.firstname;
                obj.pseudo = this.pseudo;
                obj.groups = this.groups;
                obj.address = this.address;
                return obj;
            },
            authenticate: function(clearPassword) {
                return CryptUtils.hashPassword(this.salt, clearPassword) === this.password;
            }
        }
    });
    return User;
};
