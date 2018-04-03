import React from 'react';

const About = () => (
  <div>
    <h1>About Page</h1>
    <p>Did you get here via Redux?</p>
    <div className="container">
      <h4>Material UI Grid</h4>
      <div className="row">
        <div className="col s1">1</div>
        <div className="col s1">2</div>
        <div className="col s1">3</div>
        <div className="col s1">4</div>
        <div className="col s1">5</div>
        <div className="col s1">6</div>
        <div className="col s1">7</div>
        <div className="col s1">8</div>
        <div className="col s1">9</div>
        <div className="col s1">10</div>
        <div className="col s1">11</div>
        <div className="col s1">12</div>
      </div>
      <h4>Material UI Components</h4>
      <a className="waves-effect waves-light btn">button</a>
      <a className="waves-effect waves-light btn">
        <i className="material-icons left">cloud</i>button
      </a>
      <a className="waves-effect waves-light btn">
        <i className="material-icons right">cloud</i>button
      </a>
      <a className="btn-floating btn-large waves-effect waves-light red">
        <i className="material-icons">add</i>
      </a>
      <a className="btn-floating pulse">
        <i className="material-icons">menu</i>
      </a>
      <a className="btn-floating btn-large pulse">
        <i className="material-icons">cloud</i>
      </a>
      <a className="btn-floating btn-large cyan pulse">
        <i className="material-icons">edit</i>
      </a>
    </div>
  </div>
);

export default About;
