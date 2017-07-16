'use strict';

const UtilsIndex = require('../utils');
const responsifier = UtilsIndex.ResponsifyUtils.sequelize;

/**
 * Define the List model
 * @param {Sequelize} sequelize
 * @param {Object} DataTypes
 * @returns {Model}
 */
module.exports = function (sequelize, DataTypes) {
    const List = sequelize.define("List", {
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
        state: {
            type: DataTypes.ENUM('ONGOING', 'COMPLETED'),
            allowNull: true
        }
    }, {
        paranoid: true,
        underscored: true,
        freezeTableName: true,
        classMethods: {
            associate: function (ModelIndex) {
                List.belongsToMany(ModelIndex.getModel('Group'), {
                    as: 'groups',
                    through: 'GroupList'
                });
                List.belongsToMany(ModelIndex.getModel('User'), {
                    as: 'users',
                    through: 'UserList'
                });
            }
        },
        instanceMethods: {
            responsify: function () {
                const obj = {};
                obj.uid = this.uid;
                obj.name = this.name;
                obj.state = this.state;
                return obj;
            }
        }
    });
    return List;
};
