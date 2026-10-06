"use client";

import { useCurrentUser } from "@/hooks/use-auth";
import React from "react";

// import { RequireAuth } from "@/components/providers/require-auth";
// import { AppShell } from "@/components/layout/app-shell";
// import { RepoDashboard } from "@/components/dashboard/repo-dashboard";


const DashboardPage = ()=>{
    const {data: user, isLoading} = useCurrentUser()
    console.log(user, isLoading);
    return(
        <div>DashboardPage</div>
    )
}
// export default function DashboardPage() {
//   return (
//     <RequireAuth>
//       <AppShell hideHeader>
//         <RepoDashboard/>
//       </AppShell>
//     </RequireAuth>
//   );
// }

export default DashboardPage;
    
