const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const mysql = require('mysql');
require('dotenv').config();
const watchesRoute = require('./routes/watches');

const app = express();
const port = process.env.PORT || 3000;

const connection = mysql.createConnection(process.env.SQL_DATABASE_URL);

app.use(bodyParser.json());
app.use(cors());



app.use('/', watchesRoute);

// Model for the Name table
const Name = require('./models/Name'); // Assuming you have a Name model

// Model for the Price table
const Price = require('./models/Price'); // Assuming you have a Price model

// CREATE
// Example endpoint to create a new name
app.post('/api/names', (req, res) => {
  const { name } = req.body;
  const query = `INSERT INTO name (name) VALUES ('${name}')`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else {
      res.status(201).json({ id: results.insertId, name });
    }
  });
});

// Example endpoint to create a new price
app.post('/api/prices', (req, res) => {
  const { watch_id, price, website_url } = req.body;
  const query = `INSERT INTO price (watch_id, price, website_url) VALUES (${watch_id}, '${price}', '${website_url}')`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else {
      res.status(201).json({ id: results.insertId, watch_id, price, website_url });
    }
  });
});

//GET ALL

// Example endpoint to get all names
app.get('/api/names', (req, res) => {
  const query = 'SELECT * FROM name';
  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else {
      res.json(results);
    }
  });
});

// Example endpoint to get all prices
app.get('/api/prices', (req, res) => {
  const query = 'SELECT * FROM price';
  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else {
      res.json(results);
    }
  });
});

//READ OR GET BY ID
// Example endpoint to get a name by ID
app.get('/api/names/:id', (req, res) => {
  const { id } = req.params;
  const query = `SELECT * FROM name WHERE id = ${id}`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else if (results.length === 0) {
      res.status(404).json({ message: 'Name not found' });
    } else {
      res.json(results[0]);
    }
  });
});

// Example endpoint to get a price by ID
app.get('/api/prices/:id', (req, res) => {
  const { id } = req.params;
  const query = `SELECT * FROM price WHERE id = ${id}`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else if (results.length === 0) {
      res.status(404).json({ message: 'Price not found' });
    } else {
      res.json(results[0]);
    }
  });
});

//UPDATE
// Example endpoint to update a name by ID
app.put('/api/names/:id', (req, res) => {
  const { id } = req.params;
  const { name } = req.body;
  const query = `UPDATE name SET name = '${name}' WHERE id = ${id}`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else if (results.affectedRows === 0) {
      res.status(404).json({ message: 'Name not found' });
    } else {
      res.status(200).json({ message: 'Name updated successfully' });
    }
  });
});

// Example endpoint to update a price by ID
app.put('/api/prices/:id', (req, res) => {
  const { id } = req.params;
  const { watch_id, price, website_url } = req.body;
  const query = `UPDATE price SET watch_id = ${watch_id}, price = '${price}', website_url = '${website_url}' WHERE id = ${id}`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else if (results.affectedRows === 0) {
      res.status(404).json({ message: 'Price not found' });
    } else {
      res.status(200).json({ message: 'Price updated successfully' });
    }
  });
});

//DELETE
// Example endpoint to delete a name by ID
app.delete('/api/names/:id', (req, res) => {
  const { id } = req.params;
  const query = `DELETE FROM name WHERE id = ${id}`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else if (results.affectedRows === 0) {
      res.status(404).json({ message: 'Name not found' });
    } else {
      res.status(200).json({ message: 'Name deleted successfully' });
    }
  });
});

// Example endpoint to delete a price by ID
app.delete('/api/prices/:id', (req, res) => {
  const { id } = req.params;
  const query = `DELETE FROM price WHERE id = ${id}`;

  connection.query(query, (error, results) => {
    if (error) {
      res.status(500).json({ message: error.message });
    } else if (results.affectedRows === 0) {
      res.status(404).json({ message: 'Price not found' });
    } else {
      res.status(200).json({ message: 'Price deleted successfully' });
    }
  });
});

// Start the server
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
