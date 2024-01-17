const { DataTypes } = require('sequelize');
const { sequelize } = require('../database');
const Name = require('./Name');
const Price = require('./Price');

const Product = sequelize.define('product', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
});

// Define associations
Product.belongsTo(Name);
Product.belongsTo(Price);

module.exports = Product;
