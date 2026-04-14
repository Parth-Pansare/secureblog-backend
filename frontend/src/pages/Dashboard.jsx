import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import CreatePost from '../components/CreatePost';
import CommentSection from '../components/CommentSection';

const Dashboard = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchPosts();
  }, []);

  const fetchPosts = async () => {
    setLoading(true);
    try {
      const response = await api.get('/posts');
      // Backend returns a Page object, so posts are in response.data.content
      setPosts(response.data.content || []);
      setLoading(false);
    } catch (err) {
      console.error('Error fetching posts:', err);
      setError('Failed to load posts');
      setLoading(false);
    }
  };

  return (
    <div>
      <div style={{ marginBottom: '30px' }}>
        <h2 style={{ marginBottom: '10px', fontSize: '1.8rem', fontWeight: '700' }}>Feed</h2>
        <p style={{ color: 'var(--text-muted)' }}>Welcome to your secure blog dashboard.</p>
      </div>

      {/* Show Create Post Form */}
      <CreatePost onPostCreated={fetchPosts} />

      <h3 style={{ marginBottom: '20px', fontSize: '1.2rem', fontWeight: '600', color: 'var(--text-main)' }}>Recent Posts</h3>
      
      {loading ? (
        <div style={{ textAlign: 'center', padding: '40px' }}>
          <p style={{ color: 'var(--text-muted)' }}>Loading posts...</p>
        </div>
      ) : error ? (
        <div className="error-msg">{error}</div>
      ) : posts.length === 0 ? (
        <div className="card" style={{ textAlign: 'center', padding: '40px' }}>
          <p style={{ color: 'var(--text-muted)' }}>No posts available yet. Be the first to write something!</p>
        </div>
      ) : (
        <div>
          {posts.map((post) => (
            <div key={post.id} className="card">
              <h4 style={{ fontSize: '1.3rem', marginBottom: '10px', color: 'var(--primary-color)' }}>{post.title}</h4>
              <p style={{ color: 'var(--text-main)', marginBottom: '15px', whiteSpace: 'pre-wrap' }}>{post.content}</p>
              
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '20px' }}>
                <div style={{ width: '32px', height: '32px', borderRadius: '50%', background: 'var(--border-color)', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: '0.8rem', fontWeight: '700', color: 'var(--text-muted)' }}>
                  {post.authorName?.charAt(0).toUpperCase() || 'U'}
                </div>
                <div>
                  <p style={{ fontSize: '0.85rem', fontWeight: '600', margin: '0' }}>{post.authorName || 'Unknown Author'}</p>
                  <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)', margin: '0' }}>{post.authorEmail}</p>
                </div>
              </div>
              
              {/* Comment System */}
              <CommentSection postId={post.id} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
