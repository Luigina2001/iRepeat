from nltk.chat.util import Chat, reflections

def main(utterances):

    # array contenente coppie domanda - risposta delimitate da parentesi quadre
    # r indica ciò ceh l'utente scrive nel bot (utterances), [] indicano la risposta del bot
    pairs = [
        [
            r"We|Ciao|Hey",
            ["Ciao", "Chi si rivede", "Hey"]
        ],
        [
            # (.*)= REGEX --> qualsiasi parola scriva l’utente, quella sarà racchiusa in questa categoria
            r"mi chiamo(.*)",
            # %1 ci consente di chiamare il primo REGEX
            ["Ciao %1, come va?"],
        ],
        [
            r"(.*)mi chiamo(.*)",
            # %2 ci consente di chiamare il secondo REGEX
            ["Ciao %2, come va?"],
        ],
        [
            r"il mio nome è(.*)",
            ["Ciao %1, come posso aiutarti?"],
        ],
        [
            r"(.*)come stai(.*)",
            ["Bene grazie tu?"],
        ],
        [
            r"(.*)come va(.*)",
            ["Bene grazie a te?"],
        ],
        [
            r"bene|male|benino|potrebbe andare meglio",
            ["Come posso aiutarti?"],
        ],
        [
            r"cosa sai fare?|cosa sei capace di fare?|come mi puoi aiutare?|come mi aiuti?",
            ["So darti informazioni su questa applicazione"],
        ],
        [
            r"ok|va bene|okay",
            ["Come posso esserti utile?"],
        ],
        [
            r"spiega|spiegami|cosa fa?|cosa fa questa app?|dammi le informazioni|dammi qualche informazione",
            ["Puoi creare dei quiz in base alle discipline su cui vuoi esercitarti. Torna alla homepage e clicca il pulsante crea nuovo quiz!"],
        ],
        [
            r"(.*)informa(.*)",
            ["Puoi creare dei quiz in base alle discipline su cui vuoi esercitarti. Torna alla homepage e clicca il pulsante crea nuovo quiz!"],
        ],
        [
            r"che fai?",
            ["Adesso niente, ma potrei spiegarti come funziona questa app!"],
        ],
        [
            r"come creare un nuovo quiz?|come posso creare un nuovo quiz|come si crea un nuovo quiz?|(.*)crea(.*) quiz(.*)",
            ["Torna alla homepage e clicca il pulsante crea nuovo quiz oppure vai sul tuo profilo e dal menu scegli l'opzione crea nuovo quiz.\nAttenzione! devi essere loggato per poter effettuare questa operazione"],
        ],
        [
            r"cosa sai fare?|cosa sei capace di fare?",
            ["So aiutarti ad utilizzare iRepeat!"],
        ],
        [
            # comando d'uscita --> definito dalla libreria non è possibile modificare l'input
            r"arrivederci",
            ["A presto!", "Alla prossima", "Arrivederci!"]
        ],
        [
            r"(.*)grazie(.*)",
            ["Grazie a te!"]
        ],
        [
            r"(.*)prego(.*)",
            ["Spero di esserti stato utile!"]
        ]
    ]

    chat = Chat(pairs, reflections)  # serve alla libreria per distinguere domande e risposte
    #chat.converse()  # comando per far sì che inizi la conversazione

    return chat.respond(utterances)