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
                List.belongsTo(ModelIndex.getModel('Group'), {
                    as: 'group'
                });

                List.belongsTo(ModelIndex.getModel('User'), {
                    as: 'user'
                });
            },
            associateScopes: function(ModelIndex) {
                ModelIndex.associateScopes("User"); // force scope user
                List.addScope("minimum", {
                    attributes: ["uid", "name", "state", "fav"],
                    include: [{
                         model: ModelIndex.Group.scope("minimum"),
                        as: "group"
                   },{
                       model: ModelIndex.User.scope("minimum"),
                       as: "user"
                   }]
                })
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
