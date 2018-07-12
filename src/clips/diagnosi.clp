(defrule condizione-normale-riniti-medicamentosa-gravidica
        (cellule (nome Eosinofili) (grado 0))
        (cellule (nome Mastociti) (grado 0))
        (or (cellule (nome Neutrofili) (grado 0)) (cellule (nome Neutrofili) (grado 1)))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado 0))
=>
        (assert (diagnosi(nome condizione-normale)))
        (assert (diagnosi(nome rinite-medicamentosa)))
        (assert (diagnosi(nome rinite-gravidica)))
        (printout t "Probabile diagnosi: Condizione Normale, Rinite Medicamentosa, Rinite Gravidica." crlf)
        (printout t "Ricorrere ad anamnesi per maggiore precisione nella diagnosi." crlf)
)


(defrule rinite-allergica
        (cellule (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellule (nome Mastociti) (grado ?gradoM&:(and(> ?gradoM 0) (< ?gradoM 5))))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado 0))
        ;pag 91 dice aumento leucociti e eosinofili
=>
        (assert (diagnosi(nome rinite-allergica)))
)

(defrule NARES
        (cellule (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellule (nome Mastociti) (grado 0))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(>= ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado 0))
        ;Rinite non allerigca eosinofila (NARES) - pag 91 dice aumento leucociti e eosinofili

=>
        (assert (diagnosi(nome NARES)))
)

(defrule NARESMA
        (cellule (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellule (nome Mastociti) (grado ?gradoM&:(and(> ?gradoM 0) (< ?gradoM 5))))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(>= ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado 0))
        ;NON ALLERGICO
        ;Rinite non allergica eosinofilomastocitaria (NARESMA) se associata alla presenza di mastcellule
        ;Incremento leucociti e eosinofili
        ;Incremento di eosinofili e mastociti in un paziente non allergico (da 1 a 4)
=>
        (assert (diagnosi(nome NARESMA)))
)

(defrule rinite_mastocitaria
                (cellule (nome Eosinofili) (grado 0))
        (cellule (nome Mastociti) (grado ?gradoM&:(and(> ?gradoM 0) (< ?gradoM 5))))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado 0))
=>
        (assert (diagnosi(nome rinite-mastocitaria)))
)

(defrule riniti-irritativa-virale-atrofica
                (cellule (nome Eosinofili) (grado 0))
        (cellule (nome Mastociti) (grado 0))
                (cellule (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado 0))
=>
        (assert (diagnosi(nome rinite-irritativa)))
        (assert (diagnosi(nome rinite-virale)))
        (assert (diagnosi(nome rinite-atrofica)))
        (printout t "Probabile diagnosi: Rinite irritativa, Rinite Virale, Rinite Atrofica." crlf)
        (printout t "La scelta della diagnosi più corretta tra le tre patologie riscontrare potrebbe richiedere il controllo delle cellule ciliate. Ad ogni modo è opportuno ricorrere ad anamnesi per maggiore precisione nella diagnosi." crlf)
)

(defrule rinosinusite
        (cellule (nome Eosinofili) (grado 0))
        (cellule (nome Mastociti) (grado 0))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado ?gradoB&:(and(> ?gradoB 0) (< ?gradoB 5))))
        (cellule (nome Spore) (grado 0))
        (cellule (nome Macchia) (grado ?gradoI&:(and(> ?gradoI 0) (< ?gradoI 5))))
        ;Incremento leuociti neutrofili (2 a 4):
        ;batteri extra e intracellulari e macchia infettiva -> Rinosinusite / Rinoadenoidite
=>
        (assert (diagnosi(nome rinosinusite)))
)

(defrule rinite_micotica
        (cellule (nome Eosinofili) (grado 0))
        (cellule (nome Mastociti) (grado 0))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado 0))
        (cellule (nome Spore) (grado ?gradoS&:(and(> ?gradoS 0) (< ?gradoS 5))))
        (cellule (nome Macchia) (grado ?gradoI&:(and(> ?gradoI 0) (< ?gradoI 5))))
        ;con spore e ife con eventuale presenza di macchia infettiva
        ;Rinite micotica (solitamente secondaria a infezioni batteriche, associate a poliposi nasale o in pazienti immunodepressi (AIDS))
=>
        (assert (diagnosi(nome rinit-micotica)))
)

(defrule poliposi_nasale
        (cellule (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellule (nome Mastociti) (grado ?gradoM&:(and(>= ?gradoM 0) (< ?gradoM 5))))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(>= ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado ?gradoB&:(and(>= ?gradoB 0) (< ?gradoB 5))))
        (cellule (nome Spore) (grado ?gradoS&:(and(>= ?gradoS 0) (< ?gradoS 5))))
        (cellule (nome Macchia) (grado ?gradoI&:(and(>= ?gradoI 0) (< ?gradoI 5))))
        ;pag 91 dice aumento leucociti e eosinofili
=>
        (assert (diagnosi(nome poliposi-nasale)))
)

(defrule polipo_antrocoanale
        (cellule (nome Eosinofili) (grado 0))
        (cellule (nome Mastociti) (grado 0))
        (cellule (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
        (cellule (nome Batteri) (grado ?gradoB&:(and(>= ?gradoB 0) (< ?gradoB 5))))
        (cellule (nome Spore) (grado ?gradoS&:(and(>= ?gradoS 0) (< ?gradoS 5))))
        (cellule (nome Macchia) (grado ?gradoI&:(and(>= ?gradoI 0) (< ?gradoI 5))))
=>
        (assert (diagnosi(nome poliposi-nasale)))
)
