const { Sequelize } = require('sequelize');

const sequelize = new Sequelize(process.env.SQL_DATABASE_URL, {
  dialect: 'mysql',
  host: 'localhost', // Your MySQL host
  username: 'root', // Your MySQL username
  password: '', // Your MySQL password
  database: 'watches', // Your MySQL database name
});

module.exports = { sequelize };
