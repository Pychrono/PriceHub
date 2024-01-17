const { DataTypes } = require('sequelize');
const { sequelize } = require('../database'); // Import your Sequelize instance

const Name = sequelize.define('name', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
  name: {
    type: DataTypes.STRING, // Adjust the data type based on your MySQL schema
    allowNull: false,
  },
  image_url: {
    type: DataTypes.STRING, // Adjust the data type based on your MySQL schema
     allowNull: true,
      },
    }, {
      tableName: 'name', // Set the table name explicitly
  });

module.exports = Name;