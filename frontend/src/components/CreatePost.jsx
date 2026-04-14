import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import { jwtDecode } from 'jwt-decode';

const CreatePost = ({ onPostCreated }) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decoded = jwtDecode(token);
        const role = decoded.role; 
        if (role === 'ROLE_ADMIN' || role === 'ADMIN') {
          setIsAdmin(true);
        }
      } catch (err) {
        console.error("Token decoding failed", err);
      }
    }
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');

    try {
      await api.post('/posts', { title, content });
      setMessage('Post created successfully!');
      setTitle('');
      setContent('');
      if (onPostCreated) {
        onPostCreated();
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create post.');
    }
  };

  if (!isAdmin) return null;

  return (
    <div className="card">
      <h3 style={{ marginBottom: '15px', color: 'var(--text-main)' }}>Create New Post</h3>
      {error && <div className="error-msg">{error}</div>}
      {message && <div className="success-msg">{message}</div>}
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '15px' }}>
          <label style={{ fontWeight: '500' }}>Title</label>
          <input
            type="text"
            placeholder="Post title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label style={{ fontWeight: '500' }}>Content</label>
          <textarea
            placeholder="What's on your mind?"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
            rows="4"
          />
        </div>
        <button type="submit" style={{ width: '100%', padding: '12px' }}>Publish Post</button>
      </form>
    </div>
  );
};

export default CreatePost;
