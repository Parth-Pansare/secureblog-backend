import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../api/axios';

const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await api.post('/auth/register', { name, email, password });
      alert('Registration successful! Please login.');
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Error creating account');
    }
  };

  return (
    <div style={{ marginTop: '50px' }}>
      <div className="card" style={{ maxWidth: '400px', margin: 'auto' }}>
        <h2 style={{ marginBottom: '20px', textAlign: 'center' }}>Create Account</h2>
        {error && <div className="error-msg">{error}</div>}
        <form onSubmit={handleRegister}>
          <div style={{ marginBottom: '15px' }}>
            <label style={{ fontWeight: '500' }}>Full Name</label>
            <input
              type="text"
              placeholder="Enter your name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div style={{ marginBottom: '15px' }}>
            <label style={{ fontWeight: '500' }}>Email</label>
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div style={{ marginBottom: '20px' }}>
            <label style={{ fontWeight: '500' }}>Password</label>
            <input
              type="password"
              placeholder="Choose a password (min 6 chars)"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" style={{ width: '100%', padding: '12px' }}>Register</button>
        </form>
        <p style={{ marginTop: '20px', textAlign: 'center', color: 'var(--text-muted)' }}>
          Already have an account? <Link to="/login" style={{ color: 'var(--primary-color)', textDecoration: 'none', fontWeight: '500' }}>Login</Link>
        </p>
      </div>
    </div>
  );
};

export default Register;
