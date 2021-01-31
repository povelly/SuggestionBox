export class addSondMod{
    
    title: string;
    author: string;
    expirationDate: string;
    choices: string[] = [];

    constructor(title:string, author:string, expirationDate: string, choice1: string, choice2: string, choice3: string, choice4: string, choice5: string){
        this.title = title;
        this.author = author;
        this.expirationDate = expirationDate;
        this.choices.push(choice1);
        this.choices.push(choice2);
        if(choice3!="") this.choices.push(choice3);
        if(choice4!="") this.choices.push(choice4);
        if(choice5!="") this.choices.push(choice5);
    }

}