import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import { jwtDecode } from 'jwt-decode';

const CommentSection = ({ postId }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [error, setError] = useState('');
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    fetchComments();
    checkRole();
  }, [postId]);

  const checkRole = () => {
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
  };

  const fetchComments = async () => {
    try {
      const response = await api.get(`/comments/post/${postId}`);
      setComments(response.data);
    } catch (err) {
      console.error("Error fetching comments:", err);
      setError("Could not load comments.");
    }
  };

  const handleAddComment = async (e) => {
    e.preventDefault();
    if (!newComment.trim()) return;

    try {
      await api.post(`/comments/post/${postId}`, { content: newComment });
      setNewComment('');
      fetchComments();
    } catch (err) {
      console.error("Error adding comment:", err);
      setError("Failed to add comment.");
    }
  };

  const handleDeleteComment = async (commentId) => {
    if (!window.confirm("Are you sure you want to delete this comment?")) return;

    try {
      await api.delete(`/comments/${commentId}`);
      fetchComments();
    } catch (err) {
      console.error("Error deleting comment:", err);
      alert("Failed to delete comment.");
    }
  };

  return (
    <div style={{ marginTop: '20px', padding: '15px', background: '#f1f5f9', borderRadius: '8px' }}>
      <h5 style={{ marginBottom: '15px', color: 'var(--text-main)', fontSize: '1rem' }}>Comments</h5>
      {error && <div className="error-msg" style={{ padding: '5px 10px' }}>{error}</div>}
      
      <div style={{ marginBottom: '15px' }}>
        {comments.length === 0 ? (
          <p style={{ fontStyle: 'italic', fontSize: '0.85rem', color: 'var(--text-muted)' }}>No comments yet.</p>
        ) : (
          comments.map((comment) => (
            <div key={comment.id} style={{ marginBottom: '12px', background: 'white', padding: '10px', borderRadius: '6px', border: '1px solid var(--border-color)', boxShadow: '0 1px 2px 0 rgb(0 0 0 / 0.05)' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                <div style={{ flex: 1 }}>
                  <p style={{ margin: '0', fontSize: '0.9rem', color: 'var(--text-main)' }}>{comment.content}</p>
                  <small style={{ color: 'var(--text-muted)', fontWeight: '500' }}>— {comment.user?.name || 'Anonymous'}</small>
                </div>
                {isAdmin && (
                  <button 
                    onClick={() => handleDeleteComment(comment.id)}
                    className="secondary"
                    style={{ padding: '2px 8px', fontSize: '0.75rem', marginLeft: '10px' }}
                  >
                    Delete
                  </button>
                )}
              </div>
            </div>
          ))
        )}
      </div>

      <form onSubmit={handleAddComment} style={{ display: 'flex', gap: '8px' }}>
        <input
          type="text"
          placeholder="Add a comment..."
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          style={{ flex: 1, padding: '8px', margin: '0', fontSize: '0.9rem' }}
          required
        />
        <button type="submit" style={{ padding: '8px 16px', fontSize: '0.9rem' }}>Post</button>
      </form>
    </div>
  );
};

export default CommentSection;
