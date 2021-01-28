const express = require("express");
const app = express();
const cors = require("cors");
const bodyParser = require("body-parser");
const logger = require("morgan");

const port = process.env.PORT || 3001;
//required for integration
//const usersRouter = require("./routes/users");
//const babyRouter = require("./routes/babyBag");

app.use(logger('dev'));
app.use(cors());
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

//required for integration
//app.use("/users", usersRouter);
//app.use("/babyBag", babyRouter);

app.listen(port, function(){
  console.log("Running on " + port);
});

module.exports = app;
