import React from "react";
import './tabelUsers.css'
function TabelUser({users, onChatClick}){
 return(
     <table className="table-users">
         <thead>
         <tr>
             <th>ID</th>
             <th>Name</th>
             <th>Username</th>
             <th>Password</th>
             <th>Role</th>
         </tr>
         </thead>
         <tbody className="body">
         {users.map((utilizator, index) => (
             <tr key={index}>
                 <td>{utilizator.id}</td>
                 <td>{utilizator.name}</td>
                 <td>{utilizator.username}</td>
                 <td>{utilizator.password}</td>
                 <td>{utilizator.role}</td>
                 <td style={{ border: 'none', background: 'transparent' }}>
                     <button className="chat-button" onClick={() => onChatClick(utilizator)}>Chat</button>
                 </td>
             </tr>
         ))}
         </tbody>
     </table>
 );
}
export default TabelUser;