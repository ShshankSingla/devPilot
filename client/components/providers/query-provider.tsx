"use client";
import React from 'react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const QueryProvider = ({children} : {children: React.ReactNode}) => {
    // state variable
    const[query, setQuery] = React.useState(
        ()=>new QueryClient()
    );
  return (
    // accept the client
    <QueryClientProvider client={query}>{children}</QueryClientProvider>
  )
}

export default QueryProvider