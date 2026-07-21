export interface Menu {
    idMenu?: number;
    codeMenu: number;
    libelle: string;
    route: string | null;
    icon: string;
    ordre: number;
    level: number;
    parentId: number | null;
    actif: string;
    dateCreation?: string;
    createur?: string;
    dateModification?: string;
    modificateur?: string;
}