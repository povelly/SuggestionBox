export class upMod{
    
    username: string;
    oldPassword: string;
    newPassword: string;

    constructor(username: string, oldPassword:string, newPassword: string){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.username = username;
    }

}