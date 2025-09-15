import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
 
test('renders username input field', () => {
  render(<LoginPage/> ,{ wrapper: MemoryRouter });
 
  const usernameInput = screen.getByPlaceholderText('Username');
  expect(usernameInput).toBeInTheDocument();
});
 
 