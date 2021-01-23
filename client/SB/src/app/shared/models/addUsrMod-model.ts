export class addUsrMod{
    
    username: string;
    lastName: string;
    firstName: string;
    admin: boolean;

    constructor(username:string, lastName: string, firstName: string, admin: boolean){
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.admin = admin;
    }

}
