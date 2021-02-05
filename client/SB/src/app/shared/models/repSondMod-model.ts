export class repSondMod{
    id: string;
    author: string;
    reponses:string[] = [];

    constructor(id:string, author:string, reponses:string[]){
        this.id = id;
        this.author = author;
        this.reponses = reponses;
    }


}