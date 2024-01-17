const { DataTypes } = require('sequelize');
const { sequelize } = require('../database');
const Name = require('./Name'); // Update the import path

const Price = sequelize.define('price', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
  watch_id: {
    type: DataTypes.INTEGER, // Adjust the data type based on your MySQL schema
    allowNull: true,
    foreignKey: true,
    references: {
          model: Name,
          key: 'id',
        },
    // You might need to add a foreign key constraint based on your MySQL schema
  },
  price: {
    type: DataTypes.STRING, // Adjust the data type based on your MySQL schema
    allowNull: true,
  },
  website_url: {
    type: DataTypes.STRING, // Adjust the data type based on your MySQL schema
     allowNull: true,
      },
    }, {
      tableName: 'price', // Set the table name explicitly
  }
);

module.exports = Price;