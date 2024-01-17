const express = require('express');
const router = express.Router();
const mysql = require('mysql');
require('dotenv').config();

 const connection = mysql.createConnection(process.env.SQL_DATABASE_URL);

 router.get('/api/watches', (req, res) => {
   const query = `
     SELECT name.id, name.name, name.image_url, price.price
     FROM name
     JOIN price ON name.id = price.watch_id;
   `;

   connection.query(query, (error, results) => {
     if (error) {
       console.error('Error fetching watches:', error.message);
       res.status(500).json({ error: 'Internal Server Error' });
     } else {
       // Set the Content-Type header to application/json
       res.setHeader('Content-Type', 'application/json');

       // Send the JSON response
       res.json(results);
     }
   });
 });

 module.exports = router;
