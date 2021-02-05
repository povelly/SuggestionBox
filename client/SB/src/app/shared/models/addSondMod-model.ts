export class addSondMod{
    
    title: string;
    author: string;
    expirationDate: string;
    choicesContent:string[] = [];

    constructor(title:string, author:string, expirationDate: string, choice1: string, choice2: string, choice3: string, choice4: string, choice5: string){
        this.title = title;
        this.author = author;
        this.expirationDate = expirationDate;
        this.choicesContent.push(choice1);
        this.choicesContent.push(choice2);
        if(choice3!="") this.choicesContent.push(choice3);
        if(choice4!="") this.choicesContent.push(choice4);
        if(choice5!="") this.choicesContent.push(choice5);
    }

}